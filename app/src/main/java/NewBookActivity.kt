import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.booklist_pdm_2020.R
import com.example.booklist_pdm_2020.databinding.ActivityNewBookBinding

class NewBookActivity : AppCompatActivity() {

    private lateinit var activityNewBookBinding : ActivityNewBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityNewBookBinding = ActivityNewBookBinding.inflate(layoutInflater)
        setContentView(activityNewBookBinding.root)

        val book = intent.getParcelableExtra<Book>(MainActivity.EXTRA_BOOK)
        if (book != null) {
            with (activityNewBookBinding) {
                titleEt.setText(book.title)
                isbnEt.setText(book.isbn)
                firstAuthorEt.setText(book.firstAuthor)
                publishingCompanyEt.setText(book.publishingCompany)
                editionEt.setText(book.edition.toString())
                pagesEt.setText(book.pages.toString())
            }

            val action = intent.action
            if (action != null && action == MainActivity.ACTION_VIEW_BOOK) {
                supportActionBar?.subtitle = "Book details"
                with (activityNewBookBinding) {
                    titleEt.isEnabled = false
                    isbnEt.isEnabled = false
                    firstAuthorEt.isEnabled = false
                    publishingCompanyEt.isEnabled = false
                    editionEt.isEnabled = false
                    pagesEt.isEnabled = false
                    saveBt.visibility = View.GONE
                }
            }
            else {
                // Edição
                supportActionBar?.subtitle = "Edit book"
            }
        }
        else{
            supportActionBar?.subtitle = "New book"
        }
    }

    fun onClick(view: View) {
        if (view.id == R.id.saveBt) {
            val book = Book(
                activityNewBookBinding.titleEt.text.toString(),
                activityNewBookBinding.isbnEt.text.toString(),
                activityNewBookBinding.firstAuthorEt.text.toString(),
                activityNewBookBinding.publishingCompanyEt.text.toString(),
                activityNewBookBinding.editionEt.text.toString().toInt(),
                activityNewBookBinding.pagesEt.text.toString().toInt()
            )
            val resultIntent = Intent()
            resultIntent.putExtra(MainActivity.EXTRA_BOOK, book)
            setResult(RESULT_OK, resultIntent)

            finish()
        }
    }
}