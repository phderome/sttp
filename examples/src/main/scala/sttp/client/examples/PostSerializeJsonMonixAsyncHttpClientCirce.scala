package sttp.client.examples

import sttp.client.asynchttpclient.monix.AsyncHttpClientMonixBackend

object PostSerializeJsonMonixAsyncHttpClientCirce extends App {
  import sttp.client._
  import sttp.client.circe._
  import io.circe.generic.auto._

  case class Info(x: Int, y: String)

  val postTask = AsyncHttpClientMonixBackend().flatMap { implicit backend =>
    val r = basicRequest
      .body(Info(91, "abc"))
      .post(uri"https://httpbin.org/post")

    r.send().flatMap { response =>
      println(s"""Got ${response.code} response, body:\n${response.body}""")
      backend.close()
    }
  }

  import monix.execution.Scheduler.Implicits.global
  postTask.runSyncUnsafe()
}
