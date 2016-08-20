package models.daos

import javax.inject.Inject
import models.entities.{User, UserDetails}
import models.persistence.{UserDetailsTable, UserTable, UserTypeTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.Future

/**
  * Created by Matija on 17.8.2016..
  * Class responsible to get data from a data source (User and related models)
  */
class UserDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider){
  /*
  * Config
  * */
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.driver.api._

  /*
  * table-query objects
  * */
  private val user = TableQuery[UserTable]
  private val userDetails = TableQuery[UserDetailsTable]
  private val userType = TableQuery[UserTypeTable]

  /*
  * Method fetch user by id
  * */
  def findById(id: Long): Future[Option[User]] = {
    db.run(user.filter(_.id === id).result.headOption)
  }

  /*
  * Method fetch user by email
  * */
  def findByEmail(email: String): Future[Option[User]] = {
    db.run(user.filter(_.email === email).result.headOption)
  }

  /*
  * Method save new user
  * */
  def insertUser(newUser: User): Future[User] = {
    val insert = user returning user.map(_.id) into ((user, id) => user.copy(id = id))
    db.run(insert += newUser)
  }

  /*
  * Method save new user details
  * */
  def insertUserDetails(newUserDetails: UserDetails): Future[UserDetails] = {
    val insert = userDetails returning userDetails.map(_.id) into ((userDetails, id) => userDetails.copy(id = id))
    db.run(insert += newUserDetails)
  }

/*  def update(id: Long,updatedUser: User) = {
    user.filter(_.id === id).map(u => (u.email, u.details.take(), u.details.surname, u.password))
  }*/

  /*USER DETAILS*/

  /*
  * Method fetch user details by id
  * */
  def findUserDetailsById(id: Long): Future[Option[UserDetails]] = {
    db.run(userDetails.filter(_.id === id).result.headOption)
  }
}
