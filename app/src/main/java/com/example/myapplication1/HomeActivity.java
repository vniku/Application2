package com.example.myapplication1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    //поля
    // объект пользователя, переданный нам из предыдущей активити
    User user;
    //фрагмент шапки
    HeaderFragment fragment;
    //экземпляр бд
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //кнопка, которая будет добавлять сообщения
    FloatingActcionButton newMessageBtn;
    //поле, которое будет администрировать адаптер
    ListView adapterField;
    //адаптер
    MailAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //получаем пользователя
        user=(User) Object.requireNonNull(getIntent().getExtras()).get("user");
        //скрываем шапку
        getSupportActionBar().hide();
        //создаём фрагмент
        fragment=new HeaderFragment();
        //объявляем новую транзакцию
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        //добавляем новое действие
        transaction.add(R.id.socket ,fragment);
        //применяем изменения
        transaction.commit();
        //биндим кнопку создающую новое сообщение
                newMessageBtn=findViewById(R.Id.NewMessadeBtn);
                //делаем для неё листенер
        newMessageBtn.setOnClickListener((v)->{createNewMessageDialog();});
        //биндим поле
        adapterField=findViewById(R.id.field);
        //создаём сам адаптер
        adapter=new MailAdapter(new ArrayList<Mail>(), this);
        //добавляем поля для адаптера
        adapterField.setAdapter(adapter);
        //добавляемм листенер для бд
        addListener();
    }
    //функция которая собирает и показывает диалог
    private void createNewMessageDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(HomeActivity.this);
        //дальше создаём View с разметкой нашего диалога
        FinalView root=getLayoutInflater().inflate(R.layout.dialog_new_messsage, root:null);
        //устанавливаем для диалога заголовок
        builder.setTitle("Новое сообщение")
                //добавляем наш лейаут на диалог
                .setView(root)
                //к этой кнопке приписывается слушатель нажания
                .setPositiveButton(text: "Создать",new DialogInterface().OnClickLisener(){
            @Override
            Public void onClick(DialogInterface dialog,int which) {
                //биндинги
            }
                TextView recipientTV=root.findViewById(R.id.recipient);
                TextView titleTV=root.findVewById(R.id.title);
                TextView textTV=root.findViewById(R.id.text);
                //создаём неизменяемые переменные
                final String recipient=recipientTV.getText().toString();
                final String title=titleTV.getText().toSting();
                final String text=textTV.getText().toString();
                //метод отправляющий данные в бд
                pushNewMessage(title,text,recipient);
            }
        });
        builder.create().show();
    }
    private void pushNewMessage(final String title, final String text, final String recipient) {
    //создаем ссылку для того, что бы проверить, есть ли пользователь в БД
        database. getReference( path: "user")
        .child(Utils.md5Custom(recipient))
                .addListenerForSingleValueEvent(new ValueEventListener()
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                    // Если его нет, выводим соответствующее сообщение текстом
            // и да, можно послать самому себе
            if(dataSnapshot. getValue() == null) {
                Toasty. error(getApplicationContext(),
                        message: "Данного пользователя не существует"). show();
                return;
            }
            //создаем объект письма
            Mail mail == new Mail(title)
                    text,
                    user. getEmail(),
                    recipient);
        // создаем ссылку
            // тут поясню, что в узле mail у меня содержатся зашифрованные майлы пользователей,
            // далее в них находятся письма, ключ которых это число, вычисляемое по формуле
            // максимальное число-сегодняшняя дата в миллесекундах
            // Благодаря чему новые письма будут выше
            database.getReference().child("mail").child(Utils.md5Custom(recipient))
                    .child(String.valueOf(Long. MAX_VALUE-mail.getDateInMS()))
                    .setValue(mail).addOnSuccessListener((OnSuccessListener) (aVoid)->{
                Toasty.success(getApplicationContext(), message: "Отправленно").show();
            });
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError){
        }
    });

    //Создается слушатель для БД, который срабатывает каждый раз, когда изменяются данные в узле этого пользователя. По хорошему нужен лейзи лоадинг, но это сложно для начала
    private void addListener(){
//создаем ссылку
        database.getReference( path: "mall").child(Utils.md5Custom(user.getEmail()))
//соответствующий слушатель
                .addValueEventListener(new ValueEventListener(){

        }