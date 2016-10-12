import UIKit

public class ViewController: UIViewController {
    
    @IBAction func leftPressed(sender: AnyObject) {
        GamePad.setKey(GamePadEnum.KEY_LEFT, clicked: true)
    }
    
    
    @IBAction func rightPressed(sender: AnyObject) {
        GamePad.setKey(GamePadEnum.KEY_RIGHT, clicked: true)
    }
    
    @IBAction func downPressed(sender: AnyObject) {
        GamePad.setKey(GamePadEnum.KEY_DOWN, clicked: true)
    }
    
    @IBAction func upPressed(sender: AnyObject) {
        GamePad.setKey(GamePadEnum.KEY_UP, clicked: true)
    }
    
    
    override public func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override public func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
}
