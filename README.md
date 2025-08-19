**Exception Handling**

    @ControllerAdvice tells Spring this class contains global exception handlers.
    @ExceptionHandler(MethodArgumentNotValidException.class) tells Spring: whenever validation fails, call this method.

    e.getBindingResult() → gives you all validation results.
    e.getBindingResult().getFieldErrors() → list of all field-level validation errors.
        
**ModelMapper**

        ModelMapper modelMapper = new ModelMapper();
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
-   How we are using
    ```
      List<CategoryDTO> categoriesDTOS = categories.stream()
      .map(category -> modelMapper.map(category, CategoryDTO.class))
      .toList();
    ```
    