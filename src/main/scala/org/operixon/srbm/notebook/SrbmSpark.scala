import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.mllib.linalg.{DenseMatrix, Matrix, Matrices, Vector, Vectors}
import org.wit.snr.nn.dbn.DbnAutoencoder
import org.wit.snr.nn.srbm.RbmCfg
import org.wit.snr.nn.srbm.SRBMMapReduceSpark
import org.wit.snr.nn.srbm.trainingset.TrainingSetMinst


object SrbmSark {

  def main(args: Array[String]) = {

    println("Start program")

    val spark = SparkSession.builder
                            .appName("RDD-Basic")
                            .master("local[8]")
                            .getOrCreate()

    val trainingSet = spark.read
                           .textFile("/home/artur/Pobrane/mnist/mnist_train.csv")
                           .rdd
                           .map(csvLineString => {
                             val imageData = csvLineString.split(",")
                                                          .drop(1)
                                                          .map(pixelStringValue => pixelStringValue.toDouble)
                             Matrices.dense(1, imageData.length, imageData)
                           })


    val trainingSetSize = trainingSet.count()
    val bathSize = 200.0



    val batchSum = trainingSet.repartition((trainingSetSize / bathSize).toInt)
                              .glom()
                              .map(ib => ib.reduceLeft((im1, im2) => im1.asInstanceOf[DenseMatrix]
                                                                        .multiply(im2.asInstanceOf[DenseMatrix])))
                              .reduce()

    println(s"Result  = >> $batchSum")


    /*


    spark.rdd
    val x = tset.getSamples().subList(0, 20)
    val topology = Array(784, 200, 400, 20, 10, 20, 400, 200, 784)
    val cfg = new RbmCfg()
      .setBatchSize(20)
      .learningRate(0.01)
      .setSparsneseFactor(0.65)
      .setNumberOfEpochs(10)
      .setAcceptedError(0.004)
      .persist(true)
      .setSaveVisualization(false)
      .showViz(false)
      .workDir("/databricks/driver/minst");
    val autoencoder = new DbnAutoencoder[SRBMMapReduceSpark](
      "autoencoder",
      cfg,
      topology);
    val cl = classOf[SRBMMapReduceSpark]
    autoencoder.buildTopology(cl);
    autoencoder.fit(x);*/


  }
}

