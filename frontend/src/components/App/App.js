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
                      <OrderForm orderForm={this.state.orderForm} onOrderSubmit={this.handleOrderSubmit} deleteItem={this.handleDeleteOrderItem}>
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

    handleBuyBook= (book)=>{
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
        const filteredItems = items.filter(item => item.id.id !== bookOrderId); // Ги филтрираш книгите со различен bookOrderId
        console.log(filteredItems)

        this.setState({
            orderForm: {
                items: filteredItems // Го ажурираш orderForm со новата листа без избраната книга
            }
        });
        console.log(this.state.orderForm.items)
    }


    handleOrderSubmit = () => {
        const orderForm  = this.state.orderForm;
        console.log(orderForm)

        const items = orderForm.items.map((item) => ({
            book: {
                id: item.id.id,
                name: item.name,
                description: item.description,
                discount: item.discount,
                price: item.price,
                bookCount: item.bookCount,
                status: item.status,
                category: item.category,
            },
            quantity: 1,
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
