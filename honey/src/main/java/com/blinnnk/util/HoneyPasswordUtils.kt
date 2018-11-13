@file:Suppress("NAME_SHADOWING")

package com.blinnnk.util

import com.blinnnk.extension.isNull

/**
 * @date 29/03/2018 1:16 PM
 * @author KaySaith
 */
object SafeConditions {
	
	const val minCount: Int = 7
	const val maxSameChars: Int = 3
	// 可以在数组维护更多非法字符
	@JvmField
	val illegalChars: ArrayList<String> = arrayListOf(" ")
	@JvmField
	val capitalRegex = Regex(".*[A-Z].*")
	@JvmField
	val lowercaseRegex = Regex(".*[a-z].*")
	@JvmField
	val highSafeChar = Regex(".*[!@#\$%¥^&*()_=+?].*")
}

data class ReasonText(
	var passwordCount: String = "lack of number",
	var illegalSymbol: String = "illegal chars",
	var bothNumberAndLetter: String = "you need contains number and letter both",
	var capitalAndLowercase: String = "you need contains capital and lowercase both",
	var tooMuchSameValue: String = "you have too much same value",
	var normal: String = "Normal",
	var high: String = "High",
	var strong: String = "Strong",
	var weak: String = "Weak"
)

enum class UnsafeReasons(
	var info: String,
	val code: Any? = null
) {
	
	Count(ReasonText().passwordCount, SafeConditions.minCount),
	IllegalChars(ReasonText().illegalSymbol, SafeConditions.illegalChars),
	NumberAndLetter(ReasonText().bothNumberAndLetter),
	CapitalAndLowercase(ReasonText().capitalAndLowercase),
	TooMuchSameValue(ReasonText().tooMuchSameValue, SafeConditions.maxSameChars),
	None("Congratulations");
}

enum class SafeLevel(val info: String) {
	Normal(ReasonText().normal),
	High(ReasonText().high),
	Strong(ReasonText().strong),
	Weak(ReasonText().weak);
}

// 推荐使用的封装方式
inline fun String.checkPasswordInRules(
	reason: ReasonText = ReasonText(),
	holdSafeLevel: (
		safeLevel: SafeLevel,
		reasons: UnsafeReasons
	) -> Unit
) {
	arrayListOf(
		checkValueCountIsCorrect(),
		!checkValueContainsIllegalChars(),
		checkValueContainsNumberAndLetter(),
		containsCapitalAndLowercase(),
		!containsTooMuchSameValue()
	).indexOfFirst { !it }.let {
		val reasons = arrayListOf(
			UnsafeReasons.Count.apply { info = reason.passwordCount },
			UnsafeReasons.IllegalChars.apply { info = reason.illegalSymbol },
			UnsafeReasons.NumberAndLetter.apply { info = reason.bothNumberAndLetter },
			UnsafeReasons.CapitalAndLowercase.apply { info = reason.capitalAndLowercase },
			UnsafeReasons.TooMuchSameValue.apply { info = reason.tooMuchSameValue }
		)
		/** 当全部符合标准的时候 `indexOfFirst` 会返回 `-1` */
		val reason = if (it >= 0) {
			reasons[it]
		} else {
			UnsafeReasons.None
		}
		holdSafeLevel(checkSafeLevel(), reason)
	}
}

fun String.checkValueCountIsCorrect() = count() > SafeConditions.minCount

fun String.checkValueContainsIllegalChars() = any { char ->
	SafeConditions.illegalChars.contains(char.toString())
}

fun String.checkValueContainsNumberAndLetter() =
	filterNot { it.toString().toIntOrNull().isNull() }.count() in 0 until length

fun String.containsCapitalAndLowercase() =
	matches(SafeConditions.capitalRegex) && matches(SafeConditions.lowercaseRegex)

fun String.containsTooMuchSameValue(): Boolean {
	val splitSameCharArray = arrayListOf<Char>()
	forEachIndexed { index, char ->
		if (index == 0) {
			splitSameCharArray.add(char)
		} else if (char != this[index - 1]) {
			splitSameCharArray.add(char)
		}
	}
	return count() - splitSameCharArray.count() >= SafeConditions.maxSameChars
}

fun String.checkSafeLevel(): SafeLevel {
	/** 维护更多安全条件, 增加积分的计数来重新衡量安全度 */
	val countScore = if (count() > 8) 1 else 0
	val specialCharScore = if (matches(SafeConditions.highSafeChar) && count() > 6) 2 else 0
	val safeScore = countScore + specialCharScore
	return when (safeScore) {
		0 -> SafeLevel.Weak
		1 -> SafeLevel.Normal
		2 -> SafeLevel.High
		else -> SafeLevel.Strong
	}
}