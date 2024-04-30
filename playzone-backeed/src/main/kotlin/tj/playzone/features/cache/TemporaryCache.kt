package tj.playzone.features.cache

object TemporaryCache {
    // Создание Map внутри объекта базы данных
    private val dataMap = mutableMapOf<String, String>()
    // Метод для добавления элементов в базу данных
    fun addData(key: String, value: String) {
        dataMap[key] = value
    }

    // Метод для получения значения по ключу из базы данных
    fun getData(key: String): String? {
        return dataMap[key]
    }

    // Метод для удаления элемента из базы данных
    fun removeData(key: String) {
        dataMap.remove(key)
    }

    // Метод для очистки базы данных
    fun clearData() {
        dataMap.clear()
    }

    // Метод для получения содержимого базы данных
    fun getAllData(): Map<String, String> {
        return dataMap.toMap()
    }
}