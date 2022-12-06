//package com.example.test;
//
//import static org.mockito.Mockito.verify;
//
//import org.junit.Test;
//import org.junit.Before;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
///**
// * Example local unit test, which will execute on the development machine (host).
// *
// * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
// */
//@RunWith(MockitoJUnitRunner.class)
//public class LoginPresenterTest {
//
//    @Mock
//    private LoginView view;
//    @Mock
//    private LoginPresenter presenter;
//    provate LoginService service;
//
//    @Before
//    public void setUp() throws Exception{
//        presenter = new LoginPresenter(view, service);
//    }
//
//    @Test
//    public void shouldShowErrorMessageWHenUsernameIsEmpty() throws Exception{
//        when(view.getUsername()).thenReturn("");
//        presenter.onLoginClicked();
//        verify(view).showUsernameError(R.string.username_error);
//    }
//
//
//
//    @Test
//    public void testPresenter() {
//
//        when(view.get(Username()).thenReturn("abc"));
//        when(model.isFound("abc")).thenReturn(true);
//        Presenter presenter = new Presenter(model, view);
//    }
//}