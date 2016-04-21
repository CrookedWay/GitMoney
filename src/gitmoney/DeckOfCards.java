package gitmoney;

/* -----------------------------------------------------
      Deck: a deck of cards
      ----------------------------------------------------- */
	  
	  //from:http://www.mathcs.emory.edu/~cheung/Courses/170/Syllabus/10/deck-of-cards.html
    
   public class DeckOfCards 
   {
      public static final int NCARDS = 52;
    
      private Card[] deckOfCards;         // Contains all 52 cards
      private int currentCard;            // deal THIS card in deck           
    
      public DeckOfCards( )    // Constructor
      {
   	 deckOfCards = new Card[ NCARDS ];
    
   	 int i = 0;
    
   	 for ( int rank = 1; rank <= 13; rank++ )
		for ( int suit = 1; suit <= 4; suit++ )
   		deckOfCards[i++] = new Card(rank, suit);
    
   	 currentCard = 0;
      }
    
      /* ---------------------------------
   	 shuffle(n): shuffle the deck
   	 --------------------------------- */
      public void shuffle(int n)
      {
   	 int i, j, k;
    
   	 for ( k = 0; k < n; k++ )
   	 {
   	     i = (int) ( NCARDS * Math.random() );  // Pick 2 random cards
   	     j = (int) ( NCARDS * Math.random() );  // in the deck
    
   	     /* ---------------------------------
   		swap these randomly picked cards
   		--------------------------------- */
   	     Card tmp = deckOfCards[i];
   	     deckOfCards[i] = deckOfCards[j];
   	     deckOfCards[j] = tmp;;
   	 }
    
   	 currentCard = 0;   // Reset current card to deal
      }
      /* -------------------------------------------
   	 deal(): deal deckOfCards[currentCard] out
   	 ------------------------------------------- */
      public Card deal()
      {
   	 if ( currentCard < NCARDS )
   	 {
   	    int temp = currentCard;
		currentCard ++;
		return deckOfCards[temp];
   	 }
   	 else
   	 {
   	    System.out.println("Out of cards error");
   	    return ( null );  // Error;
   	 }
      }
    
      public String deckCode()
      {
   	 String s = "";
   	 
	 int i = 0;
	 for (int j = 0; j < deckOfCards.length; j++){
		
		s += deckOfCards[j].rank();
		s += deckOfCards[j].suit();
		
	 }
   	 return ( s );
      }
	  
	  public String toString(){
	  
		String s = "";
		
		for (int i = 0; i < deckOfCards.length; i++)
			s += deckOfCards[i].toString() + "\n";
			
		return s;
	  }
   }