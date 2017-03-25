
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/zhuleqi/GitHub/LeQiEnglish/conf/routes
// @DATE:Sat Mar 25 11:20:48 CST 2017


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
