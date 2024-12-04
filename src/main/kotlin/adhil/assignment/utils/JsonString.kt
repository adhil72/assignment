package adhil.assignment.utils

class JsonString {
    companion object{
        fun fromMap(map:Map<String,Any>):String{
            var jsonString = "{"
            for((key,value) in map){
                if (value is String || value is Char){
                    jsonString += "\"$key\":\"$value\","
                }else{
                    jsonString += "\"$key\":$value,"
                }
            }
            jsonString = jsonString.substring(0,jsonString.length-1)
            jsonString += "}"
            return jsonString
        }
    }
}