package mail.technopark.bulletinBoard.firebase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import java.io.ByteArrayOutputStream

class PhotoSupport {
    fun getByteArray(image: ImageView): ByteArray {
        val bitmap = (image.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, QUALITY, stream)
        return stream.toByteArray()
    }

    fun getPhoto(array: ByteArray, image: ImageView): ImageView {
        val bitmap = BitmapFactory.decodeByteArray(array, 0, array.size)
        image.setImageBitmap(Bitmap.createScaledBitmap(bitmap, image.width, image.height, false))
        return image
    }

    companion object {
        private const val QUALITY = 90
    }
}
