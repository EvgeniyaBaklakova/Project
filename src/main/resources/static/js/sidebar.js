const $sidebar = document.querySelector('#sidebar')

sidebar()
const s = document.getElementById("sidebar");
s.className = "container-fluid";
function sidebar() {
    let html = `
      <div class="row">
        <div class="col-lg-2 bg-light mt-5 fixed-top" style="overflow-y: auto; height: 100%">
          <div class="container">
            <div class="row">
              <div class="col-lg-8">
                <nav class="nav flex-column">
                  <a href="#" class="nav-link active font-weight-bold pl-0 text-dark">Главная</a>
                  <a class="nav-link text-uppercase font-weight-100 mt-1 pl-0 text-dark">Паблик</a>
                  <a href="#" class="nav-link font-weight-100 text-dark">Вопросы</a>
                  <a href="http://localhost:63342/p_sto_7/src/main/resources/templates/tags.html?_ijt=t5d87t650uou94hj0ptj2q87ge&_ij_reload=RELOAD_ON_SAVE" class="nav-link font-weight-100 text-dark">Теги</a>
                  <a href="#" class="nav-link font-weight-100 text-dark">Участники</a>
                  <a href="#" class="nav-link font-weight-100 text-dark">Без ответа</a>
                             
                </nav>                
              </div>
            </div>
            <hr/>
            
            </div>
          </div>
        </div>
      </div>
  `
    $sidebar.insertAdjacentHTML('beforeend', html)

}
