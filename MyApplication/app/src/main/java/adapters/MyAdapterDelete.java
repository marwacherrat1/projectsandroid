package adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.contactapp.Detail_contact;
import com.example.contactapp.Model.Contact;
import com.example.contactapp.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterDelete extends RecyclerView.Adapter<com.example.contactapp.adapters.MyAdapterDelete.MyViewHolder>{
    private List<Contact> contacts;
    private List<Contact> filteredList;
    private Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapterDelete(List<Contact> contacts, Context context) {
        this.contacts = new ArrayList<Contact>();
        this.contacts.addAll(contacts);
        this.context = context;
        this.filteredList = new ArrayList<>(contacts);

    }

    // Create new views (invoked by the layout manager)

    @Override
    // -Me-   onCreateViewHolder() crée une vue et la renvoie
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_layout_delete,
                        parent, false);
        MyViewHolder vh = new MyViewHolder(itemLayoutView);
        return vh;
    }




    // -Me- onBindViewHolder() associe les données au détenteur de la vue pour une position donnée dans RecyclerView.
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.contact = contacts.get(position);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.identification.setText(contacts.get(position).getNomContact());
//        holder.service.setText(contacts.get(position).getServiceContact());
        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(contacts.get(position).getImg_url());
        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        Glide.with(context /* context */)
                .load(storageReference)
                .into(holder.photo);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return contacts.size();
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    //   -ME- l'ajout de  implements View.OnClickListener nous permet d'avoir un onClickListner
    //   a l'interieur de MyViewHolder class
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        Contact contact;
        public TextView identification;
        public TextView service;
        public ImageView photo;

        // Context is a reference to the activity that contain the the recycler view
        //we should change the constractor so that he's going to accept an onContactListener
        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            identification = itemLayoutView.findViewById(R.id.identification);
            service = itemLayoutView.findViewById(R.id.service);
            photo = itemLayoutView.findViewById(R.id.contact_photo);

            //Now we want to attach that onclickListener to the entire ViewHolder
            // "this" that we have it referes to the interface onclickListener qu'on a implementer
            itemLayoutView.setOnClickListener(this);
            itemLayoutView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                Intent myintent = new Intent(context, Detail_contact.class);
                myintent.putExtra("contact", contacts.get(pos));
                Log.i("ONEContacttttt", String.valueOf(contacts.get(pos)));
                context.startActivity(myintent);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            Log.i("fvdfvdfvdfv", "fdfvdfv");
            // Handle long click
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                Intent myintent = new Intent(context, Detail_contact.class);
                myintent.putExtra("contact", contacts.get(pos));
            }
            // Return true to indicate the click was handled
            return true;
        }

    }



}