<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="false">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Errors</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
          <ul class="list-group">
              <c:forEach var="error" items="${errors}">
                  <li class="list-group-item"><p class="text-danger m-0">${error}</p></li>
              </c:forEach>
          </ul>
      </div>
    </div>
  </div>
</div>

<script>
    debugger;
    window.onload = () => {
        debugger;
        if(document.getElementById("exampleModal")){
            var myModal = new bootstrap.Modal(document.getElementById("exampleModal"));
            myModal.show()
        }
    }
</script>