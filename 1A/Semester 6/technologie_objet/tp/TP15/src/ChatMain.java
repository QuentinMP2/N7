public class ChatMain {

    public static void main(String[] args) {
        Chat modele = new Chat();

        VueChat user1 = new VueChat(modele, "Quentin");
        VueChat user2 = new VueChat(modele, "Nicolaï");
    }
}
