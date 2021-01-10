module shared {
    requires gson;

    exports shared;
    exports shared.Models;
    exports shared.Models.Pieces;
    exports shared.Models.Pieces.SharedLogic;
    exports shared.Enums;
    exports shared.in;
    exports shared.out;
}