# tinkoff-ryazan-test-task
Test task for interview

Simple REST translation service
FEATURES:
IN -> json (translationLanguage, text)
OUT -> json (translationLanguage, text(translated))

USES GOOGLE API

DB schema:
TranslationRequest - language, ip, textTranslationId

Word - value

TEXT - many-to-many of words ??

WordTranslation - language, wordSource, wordTarget (many-to-many word-to-word)

TextTranslation - language, textSource, textTarget (many-to-many text-to-text)
