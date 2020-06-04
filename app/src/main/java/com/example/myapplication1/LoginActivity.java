package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import java.nio.Buffer;
import java.time.Instant;

public class LoginActivity extends AppCompatActivity {
    // биндим наши View
    @BindView(R.id.button3)
    Button signin;
    @BindView(R.id.editText)
    EditText emailTV;
    @BindView(R.id.editText3)
    EditText passwordTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //получение объекта кнопки
        Button openRegistrationButton = findViewById(R.id.button4);
        // биндим наши View
        ButterKnife.bind(this);
        //создание слушателя, который запустится при нажатии на соответствующую кнопку.
        openRegistrationButton.setOnClickListener({
                   //сам код события
                   Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                   startActivityForResult(intent, MainActivity.LOGIN_REQUEST_CODE);
        });

        signin.setOnClickListener(
                String editText = emailTV.getText().toString();
                //Тут опять же могут быть проверки на валидность, подробнее:
                MainActivity.firebaseDatabase.getReference(path:"user").child(Utils.md5Custom(email))
                // создаем слушатель, который сработает когда придет ответ от сервера
                // это единичный запрос, так же есть addvalueEventListener(), который добавляет
                // постоянный слушатель, который будет срабатывать каждый раз при изменении
                // значения этого и дочерних элементов
                .addListenerforSingleValueEvent(new ValueEventListener(){
                    // в качестве входных данных он получает dataSnapshot
                    // в котором содержится как нужные нам данные, которые находятся
                    // по выше заданой ссылке, так и дополнительные данные
                    @Override
                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          // преобразуем полученные данные в объект пользователя

                          User user = (User) dataSnapshot.getValue(User.class);
                          //если объект будет null, то значит что такого пользователя нет
                          // обрабатываем такой вариант событий
                          if(user == null){
                             // выводим "тост"
                             Toasty.error(getApplicationContext(),
                             message: "Неправильное имя пользователя или пароль".show();
                             // завершаем работу слушателя
                             return;
                          }


                          // возвращая её обьект пользователя
                        Intent intent = new Intent();
                          intent.putExtra(name: "user", user);
                          setResult(RESULT_OK, intent);
                          finish();
                    }
                    @Override
                    public void onCancelled(@Nontnull DatabaseError databaseError){
                        // если мы не смогли "достучаться" до БД выводим соответствующий тост
                        Toasty.error(getApplicationContext(),
                                message:"Нет соединения с сервером").show();
                    }
                });




        }
        //метод вызывается когда мы нажимаем кнопку "назад"
        // при этом убрано super.onBackPressed(), в следствии чего эта кнопка больше не исполняет
        // прежних функций. Теперь ее функция - эмулировать кнопку "домой". При приложение
        // остается в пуле запущенных

        @Override
        public void onBackPressed() {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }






