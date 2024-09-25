import './App.css';
import BookOrderService from "../../repository/repository";
import {Component} from "react";
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Books from '../Books/BookList/books'
import OrderForm from "../OrderForm/OrderForm";
import Header from "../Header/header"
import BookForm from "../Books/BookForm/BookForm";



class App extends Component{

  constructor(props) {
    super(props);
    this.state={
        books: [],
        selectedBook: {},
        orderForm: {
            currency:'MKD',
            items: []
        },
        currency:[],
        categories:[],
        status:[],
        bookCount:'',
        orderId:''
    }
  }
  render() {
    return (
        <Router>
            <Header/>
            <main>
                <Routes>
                    <Route path={"/order"} exact element={
                      <OrderForm orderForm={this.state.orderForm} onOrderSubmit={this.handleOrderSubmit} deleteItem={this.handleDeleteOrderItem} increaseQuantity={this.increaseQuantity} decreaseQuantity={this.decreaseQuantity}>
                      </OrderForm>
                    }>
                    </Route>

                    <Route path={"/books/add"} exact element={
                        <BookForm onAddBook={this.addBook}
                                          categories={this.state.categories}
                                            status = {this.state.status}
                                            currency={this.state.currency}>

                        </BookForm>
                    }>
                    </Route>
                    <Route path={"/books"} exact element={
                        <Books books={this.state.books} categories={this.state.categories} onBuy={this.handleBuyBook} onDelete={this.deleteBook} submitFilter={this.onSubmitFilter} loadBooks={this.loadBooks}>
                        </Books>
                    }>
                    </Route>
                    <Route path={"/"} exact element={
                        <Books books={this.state.books} categories={this.state.categories} onBuy={this.handleBuyBook} onDelete={this.deleteBook} submitFilter={this.onSubmitFilter} loadBooks={this.loadBooks}>
                        </Books>
                    }/>
                </Routes>
            </main>
        </Router>

    );
  }

    loadBooks = () => {
      console.log(BookOrderService.fetchBooks())
        console.log(this.state)
      BookOrderService.fetchBooks()
          .then((data)=>{
              this.setState({
                  books: data.data
              })
              console.log(data)
              console.log(data.data)
          })
    }

    onSubmitFilter =() =>{
        const selectedCategory = document.getElementById("category").value;

        BookOrderService.fetchBooksByCategory(selectedCategory)
            .then((response) => {

                this.setState({
                    books: response.data
                });
            })
            .catch((error) => {
                console.error("Error fetching books by category:", error);
            });
    }

    loadCategories =() => {
        BookOrderService.fetchCategories()
            .then((data) => {
                this.setState({
                    categories: data.data
                })
                console.log(data.data)
            })
    }

    loadCurrencies =() => {
        BookOrderService.fetchCurrencies()
            .then((data) => {
                this.setState({
                    currency: data.data
                })
                console.log(data.data)
            })
    }

    loadStatus =() => {
        BookOrderService.fetchStatus()
            .then((data) => {
                this.setState({
                    status: data.data
                })
                console.log(data.data)
            })
    }

    componentDidMount() {
        this.loadBooks();
        this.loadCategories();
        this.loadStatus()
        this.loadCurrencies()
    }

    //da prebara dali e vekjedodadena taa kniga, ako e samo da se zgolemi kolicinata ako ne e da ja dodade
    /*handleBuyBook= (book)=>{
      console.log(book)
        if(book.bookCount > 0){
        this.setState(prevState => ({
            orderForm: {
                ...prevState.orderForm,
                items: prevState.orderForm.items.concat(book)
            }
        }), () => {
            console.log(this.state.orderForm.items);
        })};
  };*/
    handleBuyBook = (book) => {
        console.log(book);

        if (book.bookCount > 0) {
            this.setState(prevState => {
                // Проверка дали книгата веќе е додадена
                const existingItem = prevState.orderForm.items.find(item => item.book.id === book.id);

                if (existingItem) {
                    // Ако книгата веќе постои, зголеми ја количината
                    const updatedItems = prevState.orderForm.items.map(item =>
                        item.book.id === book.id
                            ? { ...item, quantity: item.quantity + 1 }
                            : item
                    );
                    return {
                        orderForm: {
                            ...prevState.orderForm,
                            items: updatedItems
                        }
                    };
                } else {
                    // Ако книгата не постои, додади ја со количина 1
                    const newItem = { book: book, quantity: 1 };
                    return {
                        orderForm: {
                            ...prevState.orderForm,
                            items: [...prevState.orderForm.items, newItem]
                        }
                    };
                }
            }, () => {
                console.log(this.state.orderForm.items);
            });
        }
    };



    addBook = (name, description,price,currency,discount,status,category,bookCount) => {
        BookOrderService.addBook(name, description,price, currency,discount,status,category,bookCount)
            .then(() => {
                this.loadBooks();
            });

    }
    deleteBook = (id) => {
        BookOrderService.deleteBook(id)
            .then(() => {
                this.loadBooks();
            });
    }

    handleDeleteOrderItem = (bookOrderId) =>{
        const items = this.state.orderForm.items; // Ги земаш items од state
        console.log(items)
        const filteredItems = items.filter(item => item.book.id.id !== bookOrderId); // Ги филтрираш книгите со различен bookOrderId
        console.log(filteredItems)

        this.setState({
            orderForm: {
                items: filteredItems // Го ажурираш orderForm со новата листа без избраната книга
            }
        });
        console.log(this.state.orderForm.items)
    }

    increaseQuantity = (bookId) => {
        this.setState(prevState => ({
            orderForm: {
                ...prevState.orderForm,
                items: prevState.orderForm.items.map(item =>
                    item.book.id.id === bookId
                        ? { ...item, quantity: item.quantity + 1 }
                        : item
                )
            }
        }));
    };

    decreaseQuantity = (bookId) => {
        this.setState(prevState => ({
            orderForm: {
                ...prevState.orderForm,
                items: prevState.orderForm.items.map(item =>
                    item.book.id.id === bookId && item.quantity > 1
                        ? { ...item, quantity: item.quantity - 1 }
                        : item
                )
            }
        }));
    };


    handleOrderSubmit = () => {
        const orderForm  = this.state.orderForm;
        console.log(orderForm)

        const items = orderForm.items.map((item) => ({
            book: {
                id: item.book.id.id,
                name: item.book.name,
                description: item.book.description,
                discount: item.book.discount,
                price: item.book.price,
                bookCount: item.book.bookCount,
                status: item.book.status,
                category: item.book.category,
            },
            //da se zeme kolicinata od formata
            quantity: item.quantity,
        }));


        const formattedOrderForm = {
            currency: "MKD",
            items: items,
        };

        console.log(formattedOrderForm)


        BookOrderService.placeOrder(formattedOrderForm)
            .then((response) => {
                console.log("Order placed successfully:", response.data);
                alert("Order placed successfully. \n Your OrderId: "+ response.data.id);

                this.setState({
                    orderForm: {
                        currency: 'MKD',
                        items: []
                    },
                    orderId: response.data.id
                })
            })
            .catch((error) => {
                console.error("Error placing order:", error);
            });
    };




}

export default App;
