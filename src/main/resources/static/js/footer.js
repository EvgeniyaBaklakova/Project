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
                  href="https://stackoverflow.com//" class="logo" id="logo">
          <img src="https://upload.wikimedia.org/wikipedia/commons/0/02/Stack_Overflow_logo.svg" height="30px" alt=""/>
        </a>
      </div>

         
         
          <hr class="clearfix w-100 d-md-none pb-3">
          <div class="col-md-3 mb-md-0 mb-3">
            <h5 class="text-uppercase">Products</h5>
            <ul class="list-unstyled">
              <li>
                <a href="https://stackoverflow.co/teams/"> Teams</a>
              </li>
              <li>
                <a href="https://stackoverflow.co/advertising/"> Advertising</a>
              </li>
              <li>
                <a href="https://stackoverflow.co/collectives/">Collectives </a>
              </li>
              <li>
                <a href="https://stackoverflow.co/talent/"> Talent</a>
              </li>
            </ul>
          </div>
          <div class="col-md-3 mb-md-0 mb-3">
            <h5 class="text-uppercase">Contact</h5>
            <ul class="list-unstyled">
              <li>
                <a href="https://stackoverflow.blog/?blb=1&_ga=2.33845729.1752627041.1679862728-1158825143.1673627734">Blog</a>
              </li>
              <li>
                <a href="https://www.facebook.com/officialstackoverflow/"> Facebook</a>
              </li>
              <li>
                <a href="https://twitter.com/stackoverflow">Twitter</a>
              </li>
              <li>
                <a href="https://www.instagram.com/thestackoverflow">Instagram</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <div class="footer-copyright text-center py-lg-3">
        © 2023 Stack Exchange Inc; user contributions
      </div>
    </footer>
  `
    $footer.insertAdjacentHTML('beforeend', html)
}