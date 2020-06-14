package ru.skillbranch.devintensive.models

import androidx.core.text.isDigitsOnly

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {
    var errCounter = 0

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        val validate = validateAnswer(answer)

        return if (validate != null) {
            "${validate}\n${question.question}" to status.color
        }
        else if (question.answer.contains(answer.toLowerCase())) {
            errCounter = 0
            question = question.nextQuestion()
            "Отлично - ты справился\n${question.question}" to status.color
        } else {
            errCounter += 1
            if (errCounter > 3) {
                errCounter = 0
                question = Question.NAME
                status = Status.NORMAL
                "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            } else {
                status = status.nextStatus()
                "Это не правильный ответ!\n${question.question}" to status.color
            }
        }
    }

    private fun validateAnswer(answer: String): String? {
        if (answer.isEmpty()) return null

        when (question.name) {
            "NAME" -> {
                if (answer[0].isLowerCase()) return "Имя должно начинаться с заглавной буквы"
            }
            "PROFESSION" -> {
                if (answer[0].isUpperCase()) return "Профессия должна начинаться со строчной буквы"
            }
            "MATERIAL" -> {
                if (answer.contains(Regex("""\d"""))) return "Материал не должен содержать цифр"
            }
            "BDAY" -> {
                if (!answer.isDigitsOnly()) return "Год моего рождения должен содержать только цифры"
            }
            "SERIAL" -> {
                if (!answer.contains(Regex("""\d{7}"""))) return "Серийный номер содержит только цифры, и их 7"
            }
        }

        return null
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 255, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answer: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом всё, вопросов болшьше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question
    }
}