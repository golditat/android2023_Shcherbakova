package com.example.task4application

class Notificate {
    companion object{
        var importance:Int = 2
        var privacy:Boolean = false
        var detailedText:Boolean = false
        var buttons:Boolean = false

        fun setimportance(importance:Int){
            this.importance = importance
        }
        fun setprivacy(privacy:Boolean){
            this.privacy= privacy
        }
        fun setdetailed(detailedText:Boolean){
            this.detailedText = detailedText
        }
        fun setbuttons(buttons:Boolean){
            this.buttons = buttons
        }
        fun getimportance():Int{
            return importance
        }
        fun getprivacy():Boolean{
            return privacy
        }
        fun getdetailed():Boolean{
            return detailedText
        }
        fun getbuttons():Boolean{
            return buttons
        }
    }
}