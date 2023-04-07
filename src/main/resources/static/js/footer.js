const $footer = document.querySelector('#footer')
footer()
function footer() {
    let html = `
    <footer class="page-footer bg-light pt-4">
     
      <div class="container-fluid text-center text-md-left">
        <div class="row">
    
  
      <div class="offset-2 col-md-4 mt-md-0 mt-3">
        <a style="float: left;
                  width: 120px;"
                  href="https://kata.academy/" class="logo" id="logo">
          <img src="https://squidex.jugru.team/api/assets/sites/wsKh6rMCJn2nc6hsUqufj/kata-academy.png" height="170px" alt=""/>
        </a>
      </div>
         
          <hr class="clearfix w-100 d-md-none pb-3">
          <div class="col-md-3 mb-md-0 mb-3">
            <h5 class="text-uppercase">Главная</h5>
            <ul class="list-unstyled">
              <li>
                <a href="#"> Команды</a>
              </li>
              <li>
                <a href="#"> Реклама</a>
              </li>
              <li>
                <a href="#">Коллективы </a>
              </li>
              <li>
                <a href="#"> Талант</a>
              </li>
            </ul>
          </div>
          <div class="col-md-3 mb-md-0 mb-3">
            <h5 class="text-uppercase">Сообщество</h5>
            <ul class="list-unstyled">
              <li>
                <a href="https://vk.com/kataacademy">ВК</a>
              </li>
              <li>
                <a href="https://www.youtube.com/channel/UCsmPQu0jnkNugG6WXmxC6kw/featured">YouTube</a>
              </li>
              <li>
                <a href="https://t.me/kata_academy">Telegram</a>
              </li>
              <li>
                <a href="https://api.whatsapp.com/send/?phone=79959968596&text=Привет%21+Отправь%2C+пожалуйста%2C+нам+это+сообщение+с+номером+285728.+Мы+зарегистрируем+твое+обращение%2C+и+в+следующем+сообщении+ты+можешь+задать+свой+вопрос%21+Тебе+скоро+ответит+первый+свободный+координатор+%3D%29&app_absent=0">WhatsApp</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <div class="footer-copyright text-center py-lg-3">
        © 2023 KATA Programming Academy
      </div>
    </footer>
  `
    $footer.insertAdjacentHTML('beforeend', html)
}