package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?>{
        val parts: List<String>? = fullName?.split(" ")
        val firstName = parts?.getOrNull(0)?.takeUnless { it.isEmpty() }.apply { this }
        val lastName = parts?.getOrNull(1)?.takeUnless { it.isEmpty() }.apply { this }

        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val rule = mapOf(
            'а' to "a",
            'б' to "b",
            'в' to "v",
            'г' to "g",
            'д' to "d",
            'е' to "e",
            'ё' to "e",
            'ж' to "zh",
            'з' to "z",
            'и' to "i",
            'й' to "i",
            'к' to "k",
            'л' to "l",
            'м' to "m",
            'н' to "n",
            'о' to "o",
            'п' to "p",
            'р' to "r",
            'с' to "s",
            'т' to "t",
            'у' to "u",
            'ф' to "f",
            'х' to "h",
            'ц' to "c",
            'ч' to "ch",
            'ш' to "sh",
            'щ' to "sh'",
            'ъ' to "",
            'ы' to "i",
            'ь' to "",
            'э' to "e",
            'ю' to "yu",
            'я' to "ya").withDefault { it }

        var out: String = ""

        payload.toCharArray().forEach {
            if (it.isLowerCase()) out += rule.getValue(it) else out += rule.getValue(it.toLowerCase()).toString().capitalize()
        }

        out = out.replace(" ", divider)

        return out
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        var initials: String? = null

        if (!firstName.isNullOrBlank()) initials = firstName?.firstOrNull()?.toUpperCase().toString()

        if (!lastName.isNullOrBlank()) {
            if (initials == null) initials = ""
            initials += lastName.first().toUpperCase().toString()
        }

        return initials
    }
}