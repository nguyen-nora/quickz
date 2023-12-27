package vn.doitsolutions.quickz.model

class FakeQuestions {
    private var questions = ArrayList<Question>()

    fun generate(): FakeQuestions{
        questions.add(Question(
            "What is the capital of Vietnam?",
            listOf("Hanoi", "Ho Chi Minh", "Da Nang", "Hai Phong"),
            "Hanoi",
            null
        ))
        questions.add(
            Question(
            "What is the capital of USA?",
            listOf("New York", "Washington DC", "Los Angeles", "Chicago"),
            "Washington DC",
                userAnswer = null
            )
        )
        questions.add(Question(
            "What is the capital of Japan?",
            listOf("Tokyo", "Osaka", "Kyoto", "Nagoya"),
            "Tokyo",
            userAnswer = null
        ))
        questions.add(Question(
            "What is the capital of South Korea?",
            listOf("Seoul", "Busan", "Incheon", "Daegu"),
            "Seoul",
            userAnswer = null
        ))
        return this
    }

    fun getQuestions(): Questions {

        return Questions(
            data = this.questions,
            duration = 30
        )
    }


}