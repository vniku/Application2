package com.example.myapplication1;

import androidx.annotation.BinderThread;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public final static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public final static int LOGIN_REQUEST_CODE=8392;
    //бинд для поля сегоднейшей даты
    @BindView(R.id.dateNow)
    TextVieW dateNowTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //получаем кеш, который находится в разделе appdata
        sharedPreferences = getSharedPreferences(name:"appdata", MODE_PRIVATE);
        Butterknife.bind(target:this);
        //если пользователь не заторизовани, то запуск соответствующей activity

        if(!isAuth()){
            Intent intent = new Intent( MainActivity.this, LoginActivity.class);
            startActivityForResult(intent, LOGIN_REQUEST_CODE);

        }
        // Текущее время
        Date currentDate = new Date();
        // содаем объект, который форматирует строки как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat(pattern; "dd.MM.yyyy", Locale.getDefault());
        // создаём строку на основе форматора
        String dateText = dateFormat.format(currentDate);
        // записываем в View
        dateNowTV.setText(dateText);

    }
    //объект пользователя
    User user;
    //наш кеш
    SharedPreferences sharedPreferences;

    /**
     * Обращается к кешу и проверяет, есть ли в нем данные о пользователе
     * После чего получает данные о нем из БД
     *
     * @return есть ли данные о пользователе в БД
     */

    private boolean isAuth(){
        // получаем емайл из кеша
        String email = sharedPreferences.getString(key:"email", defValue:null);
        //если емайла нет то возвращаем отрицательный ответ
        if (email == null)
            return false;
        else {
            // возвращаем положительный результат, если емайл есть в кеше и запускаем процесс
            // получение данных о пользователе
            // раньше уже говорил, но опять же, сначала идет выполнение возвращения того,
            // есть ли пользователь в кеше, а потом будут получены данные с пользователя БД
            getUserData(email);
            return true;
        }
    }
    private void getUserData(String email) {
        firebaseDatabase.getReference("user").child(Utils.md5Custom(email))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // получаем ответ
                        user = dataSnapshot.getValue(User.class);
                        // запускаем процесс завершения загрузки
                        finishLoadActivity(user);
                    }


                }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

    // метод, который вызывается, когда активити возобновляется из другой activity, завершившийся
    // с результатом(sеtResult)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // отсеиваем все, результат которого не "ОК"
        if (resultCode == RESULT_OK) {
            // обрабатываем только те возвраты, activity которых были вызваны с помощью startActivityforResult
            if (requestCode == LOGIN_REQUEST_CODE) {
                // Проверяем, было ли что-то возвращено
                if (data == null)
                    return;
                // получаем объект пользователя
                user = (User) data.getExtras().get("user");
                // обрабатываем ситуации в которой user == null
                if (user == null)
                    return;
                // получаем объект, который может изменять кеш
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //добавляем сюда емайл
                editor.putString("email", user.getEmail());
                //применяем изменения, без этого данные не сохранятся
                editor.apply();
                // финишер для загрузки
                finishLoadActivity(user);
            }
        }
    }

    // финишер для загрузки. Передает следующей activity объект пользователя
    private void finishLoadActivity(User user) {
        // запускаем home activity и передаем в неё пользователя
        Intent intent = new Intent( packageContext: MainActivity.this, HomeActivity.class);
        intent.putExtra( name: "user", user);
        startActivity(intent):
        finish();

    }
}





