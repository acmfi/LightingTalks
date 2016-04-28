import akka.actor._

case object Lectura
case object Escritura
case object LecturaRequest
case object EscrituraRequest
case object Done

class Lector(sup: ActorRef) extends Actor {
  var counter = 0
  def receive = {
    case Lectura =>
      sup ! LecturaRequest
    case Done =>
      if (counter > 10){
        println("reading done!")
        context.stop(self)
      }else { counter +=1; self ! Lectura }
  }
}

class Escritor(sup: ActorRef) extends Actor {
  var counter = 0
  def receive = {
    case Escritura =>
      sup ! EscrituraRequest
    case Done =>
      if (counter > 10){
        println("writing done!")
        context.stop(self)
      }else { counter +=1; self ! Escritura }
  }
}

class Supervisor() extends Actor {
  var escritura = false
  def leyendo {
    println("leyendo...")
    println("leído!")
  }
  def escribiendo {
    println("escribiendo...")
    escritura = true
    escritura = false
    println("escrito!")
  }
  def receive = {
    case LecturaRequest =>
      if (!escritura){
        leyendo
        sender ! Done
      }
      else sender ! Lectura
    case EscrituraRequest =>
      if (!escritura){
        escribiendo
        sender ! Done
      }
      else sender ! Escritura
  }
}

object LectoresEscritoresTest extends App {
  override def main(args: Array[String]): Unit = {
    val system = ActorSystem("LectoresEscritoresSystem")
    val LectoresArr = new Array[ActorRef](50)
    val EscritoresArr = new Array[ActorRef](50)
    val supervisor = system.actorOf(Props(new Supervisor()), name = "sup")
    val l1 = system.actorOf(Props(new Lector(supervisor)), name = "l1")
    val e1 = system.actorOf(Props(new Escritor(supervisor)), name = "e1")
    l1 ! Lectura
    e1 ! Escritura
  }
}

