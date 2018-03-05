//
//  CategoriesVC2.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/19/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class CategoriesVC2: BaseVC {
    
    
    @IBOutlet weak var tableView : UITableView!
    
    var cat : Cat = Cat()
    weak var chooseCatVC : ChooseCatVC!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = cat.category_name
        
        tableView.delegate = self
        tableView.dataSource = self
    }
    
}

extension CategoriesVC2 : UITableViewDelegate, UITableViewDataSource{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return cat.children.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! CategoryCell
        
        cell.cat = cat.children[indexPath.row]
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let c = cat.children[indexPath.row]
        
        if c.children.isEmpty {
            self.dismiss(animated: true, completion: {
                self.chooseCatVC.filterVC?.selectedCategory = c
                self.chooseCatVC.newAdd?.selectedCategory = c

            })
            
        }else{
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "CategoriesVC2") as! CategoriesVC2
            vc.chooseCatVC = self.chooseCatVC
            vc.cat = c
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 50
    }
    
    
    
}

