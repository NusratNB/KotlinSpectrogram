import jlibrosa.LogMelSpecKt
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.nio.file.Files

import java.nio.file.Paths

fun main(){
    val generator = LogMelSpecKt()
    val pathToAudio = "D:/Nusrat/whole_training/audio/clean/dev/down/ccfd721c_nohash_0_down.wav"
    val dstMainPath = "D:/Nusrat/whole_training/csv/ccfd721c_nohash_0_down_up.csv"
    val data = generator.getAudioDate(pathToAudio)
    if (data != null) {
        generatCSV(dstMainPath, data)
    }

}

fun generatCSV(path: String, data: FloatArray ){

    Files.newBufferedWriter(Paths.get(path)).use { writer ->
        CSVPrinter(
            writer, CSVFormat.DEFAULT

        ).use { csvPrinter ->
            for (element in data){
                csvPrinter.printRecord(element)
            }
//            csvPrinter.printRecord(newAudioData.asList())
            csvPrinter.flush()
        }
    }
}