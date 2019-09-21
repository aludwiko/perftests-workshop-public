package info.ludwikowski.base

object GatlingElUtils {
  def toEL(key: String): String = s"$${${key}}"
}
