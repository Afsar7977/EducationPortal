package com.example.educationportal

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.VerticalPdfReaderState
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import java.io.File
import java.io.IOException
import java.util.Locale

@Composable
fun CoursePdfView(navHostController: NavHostController, pdfViewModel: PdfViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // on below line we are specifying theme as scaffold.
        var filePath = ""
        var pdfUri : Uri? = null
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            //resource = ResourceType.Remote("https://myreport.altervista.org/Lorem_Ipsum.pdf"),
            val bookTitle = pdfViewModel.bookData.value?.title
            when (bookTitle?.lowercase(Locale.ROOT)) {
                "cprogramming" -> {
                    filePath =
                        getFileFromAssets(LocalContext.current, "c_progrmaming.pdf").absolutePath
                }

                "corrosion" -> {
                    filePath = getFileFromAssets(LocalContext.current, "corrosion.pdf").absolutePath
                }

                "engineeringdrawing" -> {
                    filePath = getFileFromAssets(
                        LocalContext.current,
                        "engineering_drawing.pdf"
                    ).absolutePath
                }

                "physics" -> {
                     pdfUri = Uri.fromFile(File("//android_asset/physics.pdf"))

                    filePath = getFileFromAssets(LocalContext.current, "physics.pdf").absolutePath
                }
            }
            val pdfState = rememberVerticalPdfReaderState(
                resource = ResourceType.Local(pdfUri!!),
                isZoomEnable = true
            )
            /*VerticalPDFReader(
                state = pdfState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
            )*/
            val scaffoldState = rememberScaffoldState()
            PDFView(pdfState = pdfState, scaffoldState = scaffoldState)

        }
    }
}

@Composable
fun PDFView(
    pdfState: VerticalPdfReaderState,
    scaffoldState: ScaffoldState
) {
    Box(
        contentAlignment = Alignment.TopStart
    ) {
        VerticalPDFReader(
            state = pdfState,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Gray)
        )
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            LinearProgressIndicator(
                progress = pdfState.loadPercent / 100f,
                color = Color.Red,
                backgroundColor = Color.Green,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                            shape = MaterialTheme.shapes.medium
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Page: ${pdfState.currentPage}/${pdfState.pdfPageCount}",
                        modifier = Modifier.padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 4.dp,
                            top = 8.dp
                        )
                    )
                    Text(
                        text = if (pdfState.isScrolling) {
                            "Scrolling"
                        } else {
                            "Stationary"
                        },
                        color = if (pdfState.isScrolling) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            MaterialTheme.colorScheme.error
                        },
                        modifier = Modifier.padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 8.dp,
                            top = 4.dp
                        )
                    )
                }
            }
        }
        LaunchedEffect(key1 = pdfState.error) {
            pdfState.error?.let {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = it.message ?: "Error"
                )
            }
        }
    }
}

@Throws(IOException::class)
fun getFileFromAssets(context: Context, fileName: String): File = File(context.cacheDir, fileName)
    .also {
        if (!it.exists()) {
            it.outputStream().use { cache ->
                context.assets.open(fileName).use { inputStream ->
                    inputStream.copyTo(cache)
                }
            }
        }
    }