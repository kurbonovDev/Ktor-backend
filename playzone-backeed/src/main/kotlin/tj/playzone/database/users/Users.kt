package tj.playzone.database.users

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Users:Table() {
    private val login = Users.varchar("login",25)
    private val password = Users.varchar("password",25)
    private val username = Users.varchar("username",30)
    private val email = Users.varchar("email",50)


    fun insert(userDTO: UserDTO){
            transaction {
                   Users.insert {
                       it[login]=userDTO.login
                       it[password]=userDTO.password
                       it[username]=userDTO.username
                       it[email]=userDTO.email?:""
                   }
            }
    }


    fun fetchUser(login:String):UserDTO?{
        return try {
            transaction {
                val userModel= Users.select{Users.login.eq(login)}.single()
                UserDTO(
                    login=userModel[Users.login],
                    password = userModel[password],
                    username = userModel[username],
                    email = userModel[email]
                )
            }

        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }

    fun fetchUser(login: String, email: String): UserDTO? {
        return try {
            transaction {
                val userModel = Users.select { Users.login.eq(login) or Users.email.eq(email) }.singleOrNull()

                if (userModel == null) {
                    return@transaction null
                } else {
                    UserDTO(
                        login = userModel[Users.login],
                        password = userModel[Users.password],
                        username = userModel[Users.username],
                        email = userModel[Users.email]
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    fun fetchUserByEmail(emailForReset: String):UserDTO?{
        return try {
            transaction {
                val userModel= Users.select{Users.email.eq(emailForReset)}.single()
                UserDTO(
                    login=userModel[Users.login],
                    password = userModel[password],
                    username = userModel[username],
                    email = userModel[email]
                )
            }

        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }

    fun changePassword(email: String, newPassword: String) {
        try {
            transaction {
                Users.update({ Users.email.eq(email) }) {
                    it[password] = newPassword
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }





}