import java.util.*

class Main {

}

fun main(arg: Array<String>) {
    Random().nextInt(100).also {
        print("getRandomInt() generated value $it")
    }
}