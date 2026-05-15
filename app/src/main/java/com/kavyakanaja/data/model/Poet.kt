package com.kavyakanaja.data.model

data class Poet(
    val id: Int,
    val nameKn: String,
    val nameEn: String,
    val born: String,
    val died: String,
    val jnanpithYear: String,
    val bioKn: String,
    val bioEn: String,
    val notableWorks: List<String>,
    val imageRes: String = ""
)

object PoetData {
    val poets = listOf(
        Poet(
            id = 1,
            nameKn = "ಕುವೆಂಪು",
            nameEn = "Kuvempu",
            born = "1904",
            died = "1994",
            jnanpithYear = "1967",
            bioKn = "ಕುಪ್ಪಳ್ಳಿ ವೆಂಕಟಪ್ಪ ಪುಟ್ಟಪ್ಪ (ಕುವೆಂಪು) ಕನ್ನಡ ಸಾಹಿತ್ಯದ ರಾಷ್ಟ್ರಕವಿ. ಅವರ 'ರಾಮಾಯಣ ದರ್ಶನಂ' ಮಹಾಕಾವ್ಯಕ್ಕೆ ಜ್ಞಾನಪೀಠ ಪ್ರಶಸ್ತಿ ಲಭಿಸಿತು.",
            bioEn = "Kuppali Venkatappa Puttappa, known as Kuvempu, is the Rashtrakavi (National Poet) of Karnataka. He received the Jnanpith Award for his epic poem Ramayana Darshanam.",
            notableWorks = listOf("ರಾಮಾಯಣ ದರ್ಶನಂ", "ಕಾನೂರು ಹೆಗ್ಗಡತಿ", "ಮಲೆಗಳಲ್ಲಿ ಮದುಮಗಳು", "ಶ್ರೀ ರಾಮಾಯಣ ದರ್ಶನಂ")
        ),
        Poet(
            id = 2,
            nameKn = "ಡಿ.ಆರ್. ಬೇಂದ್ರೆ",
            nameEn = "D.R. Bendre",
            born = "1896",
            died = "1981",
            jnanpithYear = "1974",
            bioKn = "ದತ್ತಾತ್ರೇಯ ರಾಮಚಂದ್ರ ಬೇಂದ್ರೆ ಅಂಬಿಕಾತನಯದತ್ತ ಎಂಬ ಕಾವ್ಯನಾಮದಿಂದ ಪ್ರಸಿದ್ಧರು. ಅವರ 'ನಾಕು ತಂತಿ' ಕೃತಿಗೆ ಜ್ಞಾನಪೀಠ ಪ್ರಶಸ್ತಿ ಲಭಿಸಿತು.",
            bioEn = "D.R. Bendre, pen name Ambikatanayadatta, is one of Karnataka's greatest lyric poets. He received the Jnanpith Award for his work Naaku Tanti.",
            notableWorks = listOf("ನಾಕು ತಂತಿ", "ಸಖೀಗೀತ", "ಗರಿ", "ಉಯ್ಯಾಲೆ")
        ),
        Poet(
            id = 3,
            nameKn = "ಮಾಸ್ತಿ ವೆಂಕಟೇಶ ಅಯ್ಯಂಗಾರ್",
            nameEn = "Masti Venkatesha Iyengar",
            born = "1891",
            died = "1986",
            jnanpithYear = "1983",
            bioKn = "ಮಾಸ್ತಿ ವೆಂಕಟೇಶ ಅಯ್ಯಂಗಾರ್ ಕನ್ನಡ ಸಣ್ಣ ಕಥೆಯ ಪಿತಾಮಹ ಎಂದು ಕರೆಯಲ್ಪಡುತ್ತಾರೆ. ಅವರ 'ಚಿಕ್ಕವೀರ ರಾಜೇಂದ್ರ' ಕೃತಿಗೆ ಜ್ಞಾನಪೀಠ ಪ್ರಶಸ್ತಿ ಲಭಿಸಿತು.",
            bioEn = "Masti Venkatesha Iyengar is considered the father of Kannada short story. He received the Jnanpith Award for his novel Chikaveera Rajendra.",
            notableWorks = listOf("ಚಿಕ್ಕವೀರ ರಾಜೇಂದ್ರ", "ಸಣ್ಣ ಕಥೆಗಳು", "ತಾರ", "ಶೇಷ ಸ್ಮತಿ")
        ),
        Poet(
            id = 4,
            nameKn = "ವಿ.ಕೆ. ಗೋಕಾಕ್",
            nameEn = "V.K. Gokak",
            born = "1909",
            died = "1992",
            jnanpithYear = "1990",
            bioKn = "ವಿನಾಯಕ ಕೃಷ್ಣ ಗೋಕಾಕ್ ಕನ್ನಡ ಗೋವಾ ಚಳವಳಿಯ ನಾಯಕ. ಅವರ 'ಭಾರತ ಸಿಂಧು ರಶ್ಮಿ' ಮಹಾಕಾವ್ಯಕ್ಕೆ ಜ್ಞಾನಪೀಠ ಪ್ರಶಸ್ತಿ ಲಭಿಸಿತು.",
            bioEn = "Vinayaka Krishna Gokak led the Gokak movement for Kannada. He received the Jnanpith Award for his epic poem Bharata Sindhu Rashmi.",
            notableWorks = listOf("ಭಾರತ ಸಿಂಧು ರಶ್ಮಿ", "ನವೋದಯ", "ಸಮರಸ ಸೇವೆ", "ಕರ್ನಾಟಕ ಗೌರವ")
        ),
        Poet(
            id = 5,
            nameKn = "ಯು.ಆರ್. ಅನಂತಮೂರ್ತಿ",
            nameEn = "U.R. Ananthamurthy",
            born = "1932",
            died = "2014",
            jnanpithYear = "1994",
            bioKn = "ಉಡುಪಿ ರಾಜಗೋಪಾಲಾಚಾರ್ಯ ಅನಂತಮೂರ್ತಿ ಕನ್ನಡದ ಪ್ರಮುಖ ಕಾದಂಬರಿಕಾರ ಮತ್ತು ವಿಮರ್ಶಕ. ಅವರ 'ಸಂಸ್ಕಾರ' ಜಗತ್ ಪ್ರಸಿದ್ಧ ಕಾದಂಬರಿ.",
            bioEn = "U.R. Ananthamurthy was a major Kannada novelist, short story writer, and critic. His novel Samskara is world-famous. He received the Jnanpith Award in 1994.",
            notableWorks = listOf("ಸಂಸ್ಕಾರ", "ಭಾರತೀಪುರ", "ಅವಸ್ಥೆ", "ಘಟಶ್ರಾದ್ಧ")
        ),
        Poet(
            id = 6,
            nameKn = "ಚಂದ್ರಶೇಖರ ಕಂಬಾರ",
            nameEn = "Chandrashekara Kambara",
            born = "1937",
            died = "",
            jnanpithYear = "2010",
            bioKn = "ಚಂದ್ರಶೇಖರ ಕಂಬಾರ ಕನ್ನಡ ರಂಗಭೂಮಿ ಮತ್ತು ಕಾವ್ಯ ಕ್ಷೇತ್ರದ ಮಹಾನ್ ಕಲಾವಿದ. ಅವರ 'ಶಿವರಾತ್ರಿ' ನಾಟಕ ಮತ್ತು ಜನಪದ ಕಾವ್ಯ ಪ್ರಸಿದ್ಧ.",
            bioEn = "Chandrashekara Kambara is a major figure in Kannada theater and poetry. His works blend folk tradition with modern sensibilities. He received the Jnanpith Award in 2010.",
            notableWorks = listOf("ಶಿವರಾತ್ರಿ", "ಕರಿಮಾಯಿ", "ಜೋಕುಮಾರಸ್ವಾಮಿ", "ಹರಿಕಥಾ ಪ್ರಸಂಗ")
        )
    )
}
