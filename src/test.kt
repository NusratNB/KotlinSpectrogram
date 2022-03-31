import jlibrosa.LogMelSpecKt

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths


fun main() {
//    val strPath = "D:/Nusrat/whole_training/audio/noise/dev/down/ccfd721c_nohash_0_down_AddGaussianNoise_0.wav";
    val generator = LogMelSpecKt()
//    val filePath = File(strPath)
//    val data = generator.getMFCC(filePath.path)
//    val audioData = generator.getAudioData()
//
////    println(data.size)
////    println((audioData))
//    if (audioData != null) {
//        println(audioData.size)
//    }
//    println(data.size)
//    println(data[0].size)


    val dstMainPath = "D:/Nusrat/whole_training/csv/"
    val srcMainPath = "D:/Nusrat/whole_training/audio/"
    val clsNames = arrayOf("down", "go", "left", "no", "off", "on", "right", "stop", "up", "yes", "open_set")
    val foldNames = arrayOf("train", "dev", "test")
    val mainFoldNames = arrayOf("clean")

    for (mainFolds in mainFoldNames){
        println()
        println("######## mainFolds ##########")
        println(mainFolds)
        println("######## mainFolds ##########")
        println()
        val srcToSubFold = File(srcMainPath,mainFolds)
        val dstToSubFold = File(dstMainPath, mainFolds)
        if (!dstToSubFold.exists()){
            dstToSubFold.mkdir()
        }
        for (subFolds in foldNames){
            println()
            println("######## SubFolds ##########")
            println(subFolds)
            println("######## SubFolds ##########")
            println()
            val srcToClsPath = File(srcToSubFold, subFolds)
            val dstToClsPath = File(dstToSubFold, subFolds)
            if (!dstToClsPath.exists()){
                dstToClsPath.mkdir()
            }

            for (clsName in clsNames){
                println()
                println("######## clsName ##########")
                println(clsName)
                println("######## clsName ##########")
                println()
                val srcToAudiosPath = File(srcToClsPath, clsName)
                val dstToAudiosPath = File(dstToClsPath, clsName)
                if(!dstToAudiosPath.exists()){
                    dstToAudiosPath.mkdir()
                }
                for (i in srcToAudiosPath.listFiles()) {
                    if (!i.isDirectory) {
//                        println(i)
                        if (i.toString().endsWith(".wav")) {
                            val csvName = i.name.split(".wav")[0] + ".csv"
                            val srcWavFile = File(srcToAudiosPath, i.name)
                            val dstCSVFile = File(dstToAudiosPath, csvName)
                            if (!dstCSVFile.exists()){
                                val data = generator.getMFCC(srcWavFile.path)
                                generateCSV(dstCSVFile.path, data)
                            }
                        }
                    }
                }
            }
        }
    }



//    var arrayMap: Array<Array<String?>> = arrayOf()
//    var arrayMap = Array(data.size) {Array(data[0].size) {0} }
//    for (i in 0..data.size-1){
//        for (j in 0..data[0].size-1){
//            arrayMap[i][j] = data[i][j].toString()
//        }
//    }





}
fun generateCSV(path: String, data: Array<FloatArray> ){

    Files.newBufferedWriter(Paths.get(path)).use { writer ->
        CSVPrinter(
            writer, CSVFormat.DEFAULT

        ).use { csvPrinter ->
            for (element in data){
                csvPrinter.printRecord(element.asList())
            }
//            csvPrinter.printRecord(newAudioData.asList())
            csvPrinter.flush()
        }
    }
}


