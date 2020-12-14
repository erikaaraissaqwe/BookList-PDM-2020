import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.booklist_pdm_2020.R;
import com.example.booklist_pdm_2020.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Book> bookList;

    private BookListAdapter bookListAdapter;

    private ActivityMainBinding activityMainBinding;

    public static final String EXTRA_BOOK = "EXTRA_BOOK";

    private final int NEW_BOOK_REQUEST_CODE = 0;

    private final int EDIT_BOOK_REQUEST_CODE = 1;

    private int editedBookPosition;

    public static final String ACTION_VIEW_BOOK = "ACTION_VIEW_BOOK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        bookList = new ArrayList();
        initializeBookList();

        bookListAdapter = new BookListAdapter(this, R.layout.book_layout, bookList);

        activityMainBinding.bookListLv.setAdapter(bookListAdapter);

        registerForContextMenu(activityMainBinding.bookListLv);

        activityMainBinding.bookListLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = bookList.get(position);

                Intent viewBookIntent = new Intent(MainActivity.this, NewBookActivity.class);
                viewBookIntent.putExtra(EXTRA_BOOK, book);
                viewBookIntent.setAction(ACTION_VIEW_BOOK);
                startActivity(viewBookIntent);
            }
        });
    }

    private void initializeBookList() {
        for (int i = 0; i < 10; i++) {
            bookList.add(
                    new Book(
                            "Titulo" + i,
                            "ISBN" + i,
                            "Autor" + i,
                            "Editora" + i,
                            i,
                            i
                    )
            );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.newBookMi) {
            Intent newBookIntent = new Intent(this, NewBookActivity.class);
            startActivityForResult(newBookIntent, NEW_BOOK_REQUEST_CODE);
            return true;
        }
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu_main, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Book book = bookList.get(info.position);
        switch (item.getItemId()) {
            case R.id.editBookMi:
                Intent editBookIntent = new Intent(this, NewBookActivity.class);
                editBookIntent.putExtra(EXTRA_BOOK, book);
                editedBookPosition = info.position;
                startActivityForResult(editBookIntent, EDIT_BOOK_REQUEST_CODE);
                return true;
            case R.id.deleteBookMi:
                bookListAdapter.remove(book);
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_BOOK_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Book newBook = data.getParcelableExtra(MainActivity.EXTRA_BOOK);
            bookList.add(newBook);
            bookListAdapter.notifyDataSetChanged();
        }
        else {
            if (requestCode == EDIT_BOOK_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
                Book editedBook = data.getParcelableExtra(MainActivity.EXTRA_BOOK);
                bookListAdapter.remove(bookList.get(editedBookPosition));
                bookList.add(editedBookPosition, editedBook);
                bookListAdapter.notifyDataSetChanged();
            }
        }
    }
}

