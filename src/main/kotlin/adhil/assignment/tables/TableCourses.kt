package adhil.assignment.tables

import adhil.assignment.config.DbConfig
import adhil.assignment.dtos.CreateCourseResponse
import adhil.assignment.dtos.GetCourseRequest
import adhil.assignment.dtos.GetCoursesResponse
import adhil.assignment.modals.Course

class TableCourses {
    val connection = DbConfig.connection

    fun createTable() {
        val sql = """
            CREATE TABLE IF NOT EXISTS courses (
                id VARCHAR(255) PRIMARY KEY,
                title VARCHAR(255) NOT NULL,
                description TEXT NOT NULL,
                price DECIMAL(10,2) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                created_by VARCHAR(255) NOT NULL,
            )
        """.trimIndent()
        connection.createStatement().execute(sql)
    }

    fun insertCourse(course: Course):CreateCourseResponse {
        val sql = """
            INSERT INTO courses (title, description, price, created_by) VALUES (?, ?, ?, ?)
        """.trimIndent()
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setString(1, course.title)
        preparedStatement.setString(2, course.description)
        preparedStatement.setDouble(3, course.price)
        preparedStatement.setString(4, course.createdBy)
        preparedStatement.executeUpdate()
        return CreateCourseResponse(data = course)
    }

    fun getAllCourses(getCourseRequest: GetCourseRequest): GetCoursesResponse {
        val sql = """
            SELECT * FROM courses LIMIT ? OFFSET ?
        """.trimIndent()
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setInt(1, getCourseRequest.limit)
        preparedStatement.setInt(2, (getCourseRequest.page - 1) * getCourseRequest.limit)
        val resultSet = preparedStatement.executeQuery()
        val courses = mutableListOf<Course>()
        while (resultSet.next()) {
            courses.add(
                Course(
                    id = resultSet.getString("id"),
                    title = resultSet.getString("title"),
                    description = resultSet.getString("description"),
                    price = resultSet.getDouble("price"),
                    createdAt = resultSet.getString("created_at"),
                    createdBy = null
                )
            )
        }
        val totalCourses = connection.createStatement().executeQuery("SELECT COUNT(*) as total FROM courses").getInt("total")
        val totalPages = totalCourses / getCourseRequest.limit
        return GetCoursesResponse(courses, totalPages)
    }

    fun updateCourse(course: Course) {
        val sql = """
            UPDATE courses SET title = ?, description = ?, price = ? WHERE id = ?
        """.trimIndent()
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setString(1, course.title)
        preparedStatement.setString(2, course.description)
        preparedStatement.setDouble(3, course.price)
        preparedStatement.setString(4, course.id)
        preparedStatement.executeUpdate()
    }

    fun deleteCourse(id: String) {
        val sql = """
            DELETE FROM courses WHERE id = ?
        """.trimIndent()
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setString(1, id)
        preparedStatement.executeUpdate()
    }

    fun getCreatorCourses(userId: String, limit: Int, page: Int): GetCoursesResponse {
        val sql = """
            SELECT * FROM courses WHERE created_by = ? LIMIT ? OFFSET ?
        """.trimIndent()
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setString(1, userId)
        preparedStatement.setInt(2, limit)
        preparedStatement.setInt(3, (page - 1) * limit)
        val resultSet = preparedStatement.executeQuery()
        val courses = mutableListOf<Course>()
        while (resultSet.next()) {
            courses.add(
                Course(
                    id = resultSet.getString("id"),
                    title = resultSet.getString("title"),
                    description = resultSet.getString("description"),
                    price = resultSet.getDouble("price"),
                    createdAt = resultSet.getString("created_at"),
                    createdBy = null
                )
            )
        }
        val totalCourses = connection.createStatement().executeQuery("SELECT COUNT(*) as total FROM courses WHERE created_by = '$userId'").getInt("total")
        val totalPages = totalCourses / limit
        return GetCoursesResponse(courses, totalPages)
    }


}