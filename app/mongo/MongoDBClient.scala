package mongo




import play.api.libs.openid.Errors.AUTH_CANCEL
import reactivemongo.api.{DefaultDB, MongoConnection, MongoDriver}
import reactivemongo.bson.{BSONDocumentReader, BSONDocumentWriter, Macros, document}
import reactivemongo.api.collections.bson.BSONCollection

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by zhuleqi on 2017/3/22.
  */
object MongoDBClient {
  // My settings (see available connection options)
  val DBNAME="leqiEnglish"


  // My settings (see available connection options)
  val mongoUri = "mongodb://localhost:27017/mydb?authMode=scram-sha1"

  import ExecutionContext.Implicits.global // use any appropriate context

  // Connect to the database: Must be done only once per application
  val driver = MongoDriver()
  val parsedUri = MongoConnection.parseURI(mongoUri)
  val connection = parsedUri.map(driver.connection(_))

  // Database and collections: Get references
  val futureConnection = Future.fromTry(connection)
  def db1: Future[DefaultDB] = futureConnection.flatMap(_.database(DBNAME))
  //def db2: Future[DefaultDB] = futureConnection.flatMap(_.database("anotherdb"))
  def personCollection:Future[BSONCollection] = db1.map(_.collection("person"))

  // Write Documents: insert or update

  implicit def personWriter: BSONDocumentWriter[Person] = Macros.writer[Person]
  // or provide a custom one

  def createPerson(person: Person): Future[Unit] =
    personCollection.flatMap(_.insert(person).map(_ => {})) // use personWriter

  def updatePerson(person: Person): Future[Int] = {
    val selector = document(
      "firstName" -> person.firstName,
      "lastName" -> person.lastName
    )

    // Update the matching person
    personCollection.flatMap(_.update(selector, person).map(_.n))
  }

  implicit def personReader: BSONDocumentReader[Person] = Macros.reader[Person]
  // or provide a custom one

  def findPersonByAge(age: Int): Future[List[Person]] =
    personCollection.flatMap(_.find(document("age" -> age)). // query builder
      cursor[Person]().collect[List]()) // collect using the result cursor
  // ... deserializes the document using personReader

  // Custom persistent types
  case class Person(firstName: String, lastName: String, age: Int)

  def excute(): Unit ={
    createPerson(new Person("Robbie","Zhu",45))
    createPerson(new Person("Robbie1","Zhu",45))
    createPerson(new Person("Robbie2","Zhu",45))
    createPerson(new Person("Robbie3","Zhu",45))

    updatePerson(new Person("Robbie","Zhu",50))

  }

  def main(args: Array[String]): Unit = {
    createPerson(new Person("Robbie","Zhu",45))
    createPerson(new Person("Robbie1","Zhu",45))
    createPerson(new Person("Robbie2","Zhu",45))
    createPerson(new Person("Robbie3","Zhu",45))

    updatePerson(new Person("Robbie","Zhu",50))

    var person:Future[List[Person]] = findPersonByAge(45)
    person.map((persons)=>{
      for (p<-persons){
        println(p.age+","+p.firstName)
      }
    })
  }
}
