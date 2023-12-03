package com.example.educationportal

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.educationportal.ui.theme.EducationPortalTheme
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, pdfViewModel: PdfViewModel) {
    EducationPortalTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            val listState = rememberLazyListState()
            Scaffold(
                modifier = Modifier.padding(0.dp),
                topBar = {
                    TopAppBar(
                        // in below line we are
                        // adding title to our top bar.
                        title = {
                            // inside title we are
                            // adding text to our toolbar.
                            Text(
                                text = "EducationPortal",
                                // below line is use
                                // to give text color.
                                color = if (isSystemInDarkTheme()) Color.White else Color.Black
                            )
                        },
                        // below line is use to give background color
                        colors = TopAppBarDefaults.smallTopAppBarColors()
                    )
                },
            ) { padding ->
                @Suppress("UNUSED_EXPRESSION")
                padding
                Column {
                    val books: List<BookModel> = DEFAULT_BOOKS
                    LazyColumn(
                        modifier = Modifier.padding(padding),
                        state = listState
                    ) {
                        items(items = books) { book ->
                            Book(model = book, navController = navController, pdfViewModel = pdfViewModel)
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }
            }
        }

    }
}

val DEFAULT_BOOKS = listOf(
    BookModel(
        title = "CProgramming",
        author = "Gabrielle Zevin",
        pageCount = 416,
        "http://catalog.yale.edu/gsas/degree-granting-departments-programs/computer-science/computer-science.pdf"
    ),
    BookModel(
        title = "Corrosion",
        author = "R.F. Kuang", pageCount = 545,
        "http://catalog.yale.edu/ycps/subjects-of-instruction/electrical-engineering/electrical-engineering.pdf"
    ),
    BookModel(
        title = "EngineeringDrawing",
        author = "Stephen King",
        pageCount = 500,
        "www.tutorialspoint.com/linux_admin/linux_admin_tutorial.pdf"
    ),
    BookModel(
        title = "Physics",
        author = "Stephen King",
        pageCount = 500,
        "www.tutorialspoint.com/linux_admin/linux_admin_tutorial.pdf"
    ),
   /* BookModel(
        title = "Networking", author = "Emily St. John Mandel", pageCount = 272,
        "docs.oracle.com/cd/E19457-01/801-6632/801-6632.pdf"
    ),
    BookModel(
        title = "Data Science", author = "Leila Mottley", pageCount = 387,
        "srdas.github.io/Papers/DSA_Book.pdf"
    ),
    BookModel(
        title = "Artificial Intelligence", author = "Kate Quinn", pageCount = 435,
        "www.vssut.ac.in/lecture_notes/lecture1428643004.pdf"
    ),
    BookModel(
        title = "Big Data", author = "James S. A. Corey", pageCount = 528,
        "mrcet.com/downloads/digital_notes/IT/(R17A0528)%20BIG%20DATA%20ANALYTICS.pdf"
    ),
    BookModel(
        title = "Web Technology", author = "Johann Hari", pageCount = 357,
        "www.rgmcet.edu.in/assets/img/departments/CSE/materials/R15/3-2/Web%20Technologies.pdf"
    ),
    BookModel(
        title = "Software Development", author = "Johann Hari", pageCount = 357,
        "www.vssut.ac.in/lecture_notes/lecture1428551142.pdf"
    ),
    BookModel(
        title = "Manual Testing", author = "Johann Hari", pageCount = 357,
        "www.cs.uct.ac.za/mit_notes/software/pdfs/Chp09.pdf"
    )*/
)

data class BookModel(
    val title: String,
    val author: String,
    val pageCount: Int,
    val bookLink: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Book(modifier: Modifier = Modifier, model: BookModel, navController: NavHostController, pdfViewModel: PdfViewModel) =
    Card(
        onClick = {
            // inside on click we are displaying the toast message.
            navController.navigate(Screens.Course.route)
            pdfViewModel.updateBookData(model)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            // for our row we are adding modifier
            // to set padding from all sides.
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            // on below line inside row we are adding spacer
            Spacer(modifier = Modifier.width(5.dp))

            // on below line we are adding image to display the image.
            Image(
                // on below line we are specifying the drawable image for our image.
                painter = painterResource(id = R.drawable.pdf),

                // on below line we are specifying
                // content description for our image
                contentDescription = "Javascript",

                // on below line we are setting height
                // and width for our image.
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
                    .padding(10.dp)
            )
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Title : " + model.title)
                Text("Author : " + model.author)
                Text("${model.pageCount} pages")
            }
        }
    }