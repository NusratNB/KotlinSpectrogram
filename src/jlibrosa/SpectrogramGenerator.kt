package jlibrosa

import jlibrosa.FileFormatNotSupportedException
import jlibrosa.LogMelSpec
import jlibrosa.WavFileException

import java.io.File


class SpectrogramGenerator {

//    lateinit var bitmap: Bitmap
    lateinit var wavList:  ArrayList<Any?>
    lateinit var filename: String




    private fun getMax(data: Array<DoubleArray>): Double? {

        val maxValue: Double?
        val mutableMax: MutableList<Double> = mutableListOf()

        for (current in data){
            current.maxOrNull()?.let { mutableMax.add(it) }

        }
        maxValue = mutableMax.maxOrNull()
        return maxValue
    }

    private fun getMin(data: Array<DoubleArray>): Double? {

        val minValue: Double?
        val mutableMin: MutableList<Double> = mutableListOf()

        for (current in data){
            current.minOrNull()?.let { mutableMin.add(it) }
        }
        minValue = mutableMin.minOrNull()
        return minValue
    }


    fun minMaxScaling(data: Array<DoubleArray>): Array<DoubleArray> {
        val minVal = getMin(data)
        val maxVal = getMax(data)
        val rows = data.size
        val columns = data[0].size
        val normalizedData: Array<DoubleArray> = Array(rows){ DoubleArray(columns) }

        for(i in 0 until data.size){
            for (j in 0 until data[i].size){
                if (maxVal != null) {
                    normalizedData[i][j] = (data[i][j] - minVal!!)/(maxVal - minVal)
                }
            }
        }
        return normalizedData
    }


    private fun getSDcardFile(groupPath: File){
        wavList = ArrayList()
        for (i in groupPath.listFiles().indices) {
            val childFile = groupPath.listFiles()[i]
            if (!childFile.isDirectory) {
                if (childFile.toString().endsWith(".wav")) {
                    wavList.add(childFile.name)
                }
            }
        }

    }

    @JvmName("getFilename1")
    fun getFilename(): String{
        return filename
    }

//    fun generateImage(ctx: Context): Bitmap {
//        val sdcardFile = Environment.getExternalStorageDirectory()
//        val wavnames = getSDcardFile(sdcardFile)
//        // Make the file path
//        val sdPath = Environment.getExternalStorageDirectory().absolutePath
//        filename = wavList.get(wavList.size-1).toString()
//        val path = sdPath + File.separator + filename
//        var logmelspec = Array(0) {
//            DoubleArray(
//                0
//            )
//        }
//        try {
//            logmelspec = LogMelSpec.main(path)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } catch (e: WavFileException) {
//            e.printStackTrace()
//        } catch (e: FileFormatNotSupportedException) {
//            e.printStackTrace()
//        }
//
//        var normalizedLogmelSpec = minMaxScaling(logmelspec)
//        var spectoImg = SpectrogramView(ctx, normalizedLogmelSpec)
//        bitmap = spectoImg.getBitmap()
//        val width: Int = bitmap.getWidth()
//
//        val height: Int = bitmap.getHeight()
//        val matrix: Matrix = Matrix()
//
//
//
//        matrix.postRotate(-90.0f)
//
//        return Bitmap.createBitmap(
//            bitmap,
//            0,
//            0,
//            bitmap.getWidth(),
//            bitmap.getHeight(),
//            matrix,
//            true
//        )
//    }
}