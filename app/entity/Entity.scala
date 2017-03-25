package entity

import scala.beans.BeanProperty

/**
  * Created by zhuleqi on 2017/3/22.
  */
abstract class Entity {
  /**
    * id
    */
 val id: String

  /**
    * 创建时间
    */
var createDate: Long

}
