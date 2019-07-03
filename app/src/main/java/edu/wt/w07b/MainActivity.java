package edu.wt.w07b;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    SearcherAPI myAPI;
    EditText editQueryString;
    List<Question> questions;
    ListView questionsListView;
    Context context = this;
    TextView questionsFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editQueryString = findViewById(R.id.editQueryString);
        questions = new ArrayList<Question>();
        questionsListView = findViewById(R.id.questionsListView);
        questionsListView.setAdapter(new ItemQuestionAdapter());
        questionsFound = findViewById(R.id.questionsFound);

        createSearcherAPI();
    }

    // Utworzenie obiektu wykonującego zapytania do API
    private void createSearcherAPI() {
        // Filtr GSON będzie automatycznie tłumaczył pobrany plik JSON na obiekty Javy
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        // Fabryka buduje obiekt retrofit służący do pobierania danych
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SearcherAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // Fabryka buduje obiekt dla naszego API
        myAPI = retrofit.create(SearcherAPI.class);
    }


    // Uruchomienie zapytania do API po kliknięciu przycisku.
    // Tytuł szukanego zapytania pochodzi z pola tekstowego editQueryString
    // Klasa QuestionsCallback obsłuży metodę zwrotną
    public void setCatalogContent(View button) {
        String title = editQueryString.getText().toString();
        myAPI.getQuestions(title).enqueue(questionsCallback);
    }

    // Funkcje zwrotne wywoływane po zakończeniu zapytania API
    // Zawsze trzeba zdefiniować zachowanie Retrofita po udanym wywołaniu (onResponse)
    // i po błędzie (onFailure)

    Callback<List<Question>> questionsCallback = new Callback<List<Question>>() {
        @Override
        public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
            if (response.isSuccessful()) {
                // Pobranie danych z odpowiedzi serwera
                questions = response.body();

                // Odświeżenie widoku listy i informacji o pobranych danych
                ((ItemQuestionAdapter)questionsListView.getAdapter()).notifyDataSetChanged();
                questionsListView.setSelectionAfterHeaderView();
                questionsFound.setText("We've found " + questions.size() + " movies");
            } else {
                Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<List<Question>> call, Throwable t) {
            t.printStackTrace();
        }
    };

    // Adapter pozwalający wyświetlać listę obiektów w liście
    private class ItemQuestionAdapter extends BaseAdapter {
        LayoutInflater layoutInflater;

        public ItemQuestionAdapter() {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return questions.size();
        }

        @Override
        public Object getItem(int position) {
            return questions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View rowView, ViewGroup parent) {
            if(rowView == null) {
                rowView = layoutInflater.inflate(R.layout.item_question,null);
            }
            TextView questionText = rowView.findViewById(R.id.question);
            TextView questionLink;
            questionLink = rowView.findViewById(R.id.link);
            Question currentQuestion = questions.get(position);
            questionText.setText(currentQuestion.getTitle());
            questionLink.setText(currentQuestion.getLink());

            return rowView;
        }
    }
}

