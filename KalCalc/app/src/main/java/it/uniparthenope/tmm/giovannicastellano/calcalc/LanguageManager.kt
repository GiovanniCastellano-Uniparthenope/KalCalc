package it.uniparthenope.tmm.giovannicastellano.calcalc

class LanguageManager {
    public var englishTexts = ArrayList<String>()
    public var italianTexts = ArrayList<String>()

    public fun getStringAt(i : Int) : String
    {
        if(static.language == LANGUAGE.ITALIAN)
        {
            if(i >= italianTexts.size)
                return ""
            else
                return italianTexts[i]
        }
        else
        {
            if(i >= englishTexts.size)
                return ""
            else
                return englishTexts[i]
        }
    }
}