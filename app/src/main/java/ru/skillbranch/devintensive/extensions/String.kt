package ru.skillbranch.devintensive.extensions

fun String.truncate(count: Int = 16): String {
    return if (this.length > count) this.substring(0, count).trimEnd() + "..." else this.trimEnd()
}

fun String.stripHtml(): String {
    return this.replace(Regex("""[\s]{2}|(\<(/?[^>]+)>)|[&<>'"]"""), "")
}