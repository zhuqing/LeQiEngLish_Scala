package controllers

import java.util.UUID
import javax.inject.{Inject, Singleton}

import mongo.MongoDBClient
import play.api._
import play.api.mvc._


/**
  * Created by zhuleqi on 2017/3/22.
  */
@Singleton
class UserController @Inject extends Controller{

  def addUser(name:String,passowrd:String)=Action{
      // User userEntity = new User(UUID.randomUUID().toString);
    println(name)

       Ok("ok,name="+name+",password="+passowrd)

  }

  def getUser()=Action{
    MongoDBClient.excute()
    Ok("name=")
  }

  def getUserById(id:Long) = Action{
    Ok("id,"+id)
  }
}
