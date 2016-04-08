<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form role="form" action="/new_restaurant" method="post" class="col-md-3  pull-right">
    <fieldset>
        <legend>NEW RESTAURANT</legend>
        <div class="form-group">
            <label for="name">Restaurant name:</label>
            <input type="text" class="form-control" id="name" name="name" required>
        </div>
        <div class="form-group">
            <label for="discript">Discription:</label>
            <textarea  class="form-control text-area" rows="10"  id="discript" name="discript" style="resize: vertical"></textarea>
        </div>
        <div class="form-group">
            <label for="image">URL-image:</label>
            <input type="url" class="form-control" id="image" name="image">
        </div>
        <button type="submit" class="btn btn-success pull-right">Add</button>
    </fieldset>
</form>
