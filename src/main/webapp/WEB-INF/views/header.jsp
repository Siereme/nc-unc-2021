<form id="find" action="find" method="post"></form>
<form id="findAll" action="all" method="get"></form>
<form id="add" action="handle/page-add" method="post"></form>

<header>
    <div class="px-3 py-2 bg-dark text-white">
      <div class="container mw-100 p-0">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-end">
          <ul class="nav col-12 col-lg-auto my-2 justify-content-center my-md-0 text-small">
            <li>
              <a href="http://localhost:8080/films/all" class="nav-link text-secondary">
                Films
              </a>
            </li>
            <li>
              <a href="http://localhost:8080/genres" class="nav-link text-white">
                Genres
              </a>
            </li>
            <li>
              <a href="#" class="nav-link text-white">
                Actors
              </a>
            </li>
            <li>
              <a href="#" class="nav-link text-white">
                Directors
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="px-3 py-2 border-bottom mb-3">
      <div class="container d-flex justify-content-between mw-100 w-100">
        <form class="d-flex col-12 col-lg-auto mb-2 mb-lg-0 me-lg-auto">
            <input class="form-control col-1" name="tittle" form="find" placeholder="Search..."/>
            <input type="submit" class="btn btn-outline-dark  ms-2" name="find" value="Search" form="find"  />
            <input type="submit" class="btn btn-outline-dark  ms-2" name="find" value="Search All" form="findAll"  />
            <input type="submit" class="btn btn-outline-dark  ms-2" value="Add" form="add" />
        </form>

        <div class="text-end">
          <button type="button" class="btn btn-light text-dark me-2">Login</button>
          <button type="button" class="btn btn-primary">Sign-up</button>
        </div>
      </div>
    </div>
  </header>