package com.example.myapplication1;

import androidx.annotation.NonNull;

public class MyFragment {
    Object param;

    public MyFragment(Object param) {
        this.param = param;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        //при создании фрагмента мы задаем руту значение
        View root = inflater.inflate(R.layout.fragment_my, container, false);
        //биндим все, как источник указываем рут
        //в проекте мы используем заменитель этого, но канонический бинд выглядит так!
        TextView textView = root.findViewById(R.id.textView);
        //возвращаем наш рут
        return root;
    }

    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    MyFragment fragment = new MyFragment();
            transaction.add(R.id.socket,fragment);
            transaction.commit();
            MyAdapter adapter = new MyAdapter(arrayList<Object>(), getApplicationContext());
            listView.setAdapter(adapter);

}

}
