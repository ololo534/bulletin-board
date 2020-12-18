package mail.technopark.bulletinBoard.firebase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

class PhotoSupport {
    fun getByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, QUALITY, stream)
        return stream.toByteArray()
    }

    fun getPhoto(array: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(array, 0, array.size)
    }

    companion object {
        private const val QUALITY = 90
    }
}
