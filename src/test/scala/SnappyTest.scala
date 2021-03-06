import java.io.File

import org.scalatest.funsuite.AnyFunSuite
import scala.util.{Failure, Success}
import com.github.gekomad.scalacompress.Compressors._

class SnappyTest extends AnyFunSuite {

  test("snappy compress") {
    val tmpDir  = Util.createTmpDir(suiteName)
    val file    = "aa.txt"
    val src     = new File( getClass.getResource(file).getPath).getAbsolutePath
    val srcDecr = s"$tmpDir/adec"
    val dest    = s"$tmpDir"
    new File(srcDecr).mkdirs()
    snappyCompress(src, dest) match {
      case Failure(e) => assert(false, e)
      case Success(statistics) =>
        println("-----------\n" + Util.toString(statistics) + "-----------\n")
        snappyDecompress(dest + s"/$file.sz", srcDecr) match {
          case Failure(exception) => assert(false, exception)
          case Success(statistics) =>
            println("-----------\n" + Util.toString(statistics) + "-----------\n")

            assert(scala.io.Source.fromFile(src).mkString == scala.io.Source.fromFile(srcDecr + s"/$file").mkString)
        }
    }
  }

}
