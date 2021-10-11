package com.c1ctech.jetpackcomposeexp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.c1ctech.jetpackcomposeexp.ui.theme.JetpackComposeExpTheme
import kotlinx.android.parcel.Parcelize
import androidx.compose.foundation.Image as Image


val bookList: List<Book> = listOf(
    Book(R.drawable.anny_frank, "The Diary of A Young Girl", "Anne Frank"),
    Book(R.drawable.alexander_the_great, "Alexander the Great", "Jacob Abbott"),
    Book(R.drawable.autobiography_of_a_yogi, "Autobiography of a Yogi", "Paramahansa Yogananda"),
    Book(R.drawable.fluffy_and_me, "Fluffy and Me", "Anita Krishan"),
    Book(R.drawable.my_inventions, "My Inventions", "Nikola Tesla"),
    Book(R.drawable.the_enchanted_castle, "The Enchanted Castle", "E. Nesbit"),
    Book(R.drawable.the_secret_garden, "The Secret Garden", " Frances Hodgson Burnett")
)


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeExpTheme {
                Column(modifier = Modifier.fillMaxSize()) {
                    //Material Design top app bar displays information and actions relating to the current screen.
                    TopAppBar(title = {
                        Text("BookApp")
                    })
                    BookList(bookList, this@MainActivity)
                }
            }
        }
    }
}

@Parcelize
data class Book(
    @DrawableRes val imageResource: Int,
    val bookTitle: String,
    val bookAuthor: String
) : Parcelable

@Composable
fun BookList(books: List<Book>, context: Context) {
    //The vertically scrolling list that only composes and lays out the currently visible items.
    LazyColumn() {
        items(books) { book ->
            BookListItem(book, context)
        }
    }
}

@Composable
fun BookListItem(book: Book, context: Context) {
    //used to make a CardView.
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                //open DetailActivity on item click
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("bookData", book)
                startActivity(context, intent, null)

            }
    ) {
        //places its children in a horizontal sequence.
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            BookImage(book)
            //places its children in a vertical sequence.
            Column(
                modifier = Modifier
                    .weight(6f, true)
                    .padding(20.dp, 0.dp, 0.dp, 0.dp),
            ) {
                //used to display text
                Text(
                    text = book.bookTitle,
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.Red
                )
                Text(
                    text = "by ${book.bookAuthor}",
                    style = MaterialTheme.typography.subtitle2,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue,
                )

            }
        }
    }
}


@Composable
fun BookImage(book: Book?) {
    //used to display an image.
    Image(
        painter = painterResource(book!!.imageResource),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .size(84.dp)
            .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
    )
}

//Preview can be applied to @Composable methods with no parameters
//to show them in the Android Studio preview.
@Preview
@Composable
fun bookListPreview() {
    JetpackComposeExpTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(title = {
                Text("BookApp")
            })
            BookList(
                bookList,
                LocalContext.current
            )
        }
    }
}


